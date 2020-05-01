package com.kundy.excelutils.utils;

import com.kundy.excelutils.constant.ExcelFormat;
import com.kundy.excelutils.entity.ExcelHeaderInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kundy
 * @create 2019/2/15 10:28 AM
 * <p>
 * excel报表导出工具类
 */

public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    public static final int ROW_ACCESS_WINDOW_SIZE = 100;
    public static final int SHEET_MAX_ROW = 100000;

    /**
     * 保存待导出的数据
     */
    private List list;
    /**
     * 保存表头信息
     */
    private List<ExcelHeaderInfo> excelHeaderInfos;
    /**
     * 格式化字段
     */
    private Map<String, ExcelFormat> formatInfo;

    public ExcelUtils(List list, List<ExcelHeaderInfo> excelHeaderInfos) {
        this.list = list;
        this.excelHeaderInfos = excelHeaderInfos;
    }

    public ExcelUtils(List list, List<ExcelHeaderInfo> excelHeaderInfos, Map<String, ExcelFormat> formatInfo) {
        this.list = list;
        this.excelHeaderInfos = excelHeaderInfos;
        this.formatInfo = formatInfo;
    }

    public Workbook getWorkbook() {
        Workbook workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
        String[][] datas = transformData();
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        CellStyle style = setCellStyle(workbook);
        int pageRowNum = 0;
        Sheet sheet = null;

        long startTime = System.currentTimeMillis();
        LOGGER.info("开始处理excel文件。。。");

        for (int i = 0; i < datas.length; i++) {
            // 如果是每个sheet的首行
            if (i % SHEET_MAX_ROW == 0) {
                // 创建新的sheet
                sheet = createSheet(workbook, i);
                pageRowNum = 0; // 行号重置为0
                for (int j = 0; j < getHeaderRowNum(excelHeaderInfos); j++) {
                    sheet.createRow(pageRowNum++);
                }
                createHeader(sheet, style);
            }
            // 创建内容
            Row row = sheet.createRow(pageRowNum++);
            createContent(row, style, datas, i, fields);
        }
        LOGGER.info("处理文本耗时" + (System.currentTimeMillis() - startTime) + "ms");
        return workbook;
    }

    // 创建表头
    private void createHeader(Sheet sheet, CellStyle style) {
        // 分类、品牌、品牌ID等多个表头
        for (ExcelHeaderInfo excelHeaderInfo : excelHeaderInfos) {
            Integer lastRow = excelHeaderInfo.getLastRow();
            Integer firstRow = excelHeaderInfo.getFirstRow();
            Integer lastCol = excelHeaderInfo.getLastCol();
            Integer firstCol = excelHeaderInfo.getFirstCol();

            // 行距或者列距大于0才进行单元格融合
            if ((lastRow - firstRow) != 0 || (lastCol - firstCol) != 0) {
                sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
            }
            // 获取当前表头的首行位置
            Row row = sheet.getRow(firstRow);
            // 在表头的首行与首列位置创建一个新的单元格
            Cell cell = row.createCell(firstCol);
            // 赋值单元格
            cell.setCellValue(excelHeaderInfo.getTitle());
            cell.setCellStyle(style);
            // todo  what for?
            sheet.setColumnWidth(firstCol, sheet.getColumnWidth(firstCol) * 17 / 12);
        }
    }

    // 创建正文
    private void createContent(Row row, CellStyle style, String[][] content, int i, Field[] fields) {
        List<String> columnNames = getBeanProperty(fields);
        for (int j = 0; j < columnNames.size(); j++) {
            // 如果格式化Map为空，默认为字符串格式
            if (formatInfo == null) {
                row.createCell(j).setCellValue(content[i][j]);
                continue;
            }
            if (formatInfo.containsKey(columnNames.get(j))) {
                switch (formatInfo.get(columnNames.get(j)).getValue()) {
                    case "DOUBLE":
                        row.createCell(j).setCellValue(Double.parseDouble(content[i][j]));
                        break;
                    case "INTEGER":
                        row.createCell(j).setCellValue(Integer.parseInt(content[i][j]));
                        break;
                    case "PERCENT":
                        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(style);
                        cell.setCellValue(Double.parseDouble(content[i][j]));
                        break;
                    case "DATE":
                        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd HH:mm:ss"));
                        Cell cell1 = row.createCell(j);
                        cell1.setCellStyle(style);
                        row.createCell(j).setCellValue(this.parseDate(content[i][j]));
                }
            } else {
                row.createCell(j).setCellValue(content[i][j]);
            }
        }
    }

    // 将原始数据转成二维数组
    private String[][] transformData() {
        int dataSize = this.list.size();
        // dataSize 条记录就设置 dataSize 行
        String[][] datas = new String[dataSize][];
        // 获取报表的列数 (获取实体类的所有字段，从而间接知道一共多少列)
        // todo 可以提取出来成为成员变量，避免每次都反射获取字段
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        // 获取实体类的字段名称数组
        List<String> columnNames = this.getBeanProperty(fields);
        for (int i = 0; i < dataSize; i++) {
            datas[i] = new String[fields.length];
            for (int j = 0; j < fields.length; j++) {
                try {
                    // 赋值 BeanUtils 很方便的帮助我们对一个实体类进行字段的赋值与字段值的获取
                    // 获取实体list.get(i)中名称为columnNames.get(j)这个字段的值
                    datas[i][j] = BeanUtils.getProperty(list.get(i), columnNames.get(j));
                } catch (Exception e) {
                    LOGGER.error("获取对象属性值失败");
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

    // 获取实体类的字段名称数组
    private List<String> getBeanProperty(Field[] fields) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
            // todo 这里可以直接获取到名字 field.getName()
            String[] strings = field.toString().split("\\.");
            String columnName = strings[strings.length - 1];
            columnNames.add(columnName);
        }
        return columnNames;
    }

    // 新建表格
    private static Sheet createSheet(Workbook workbook, int i) {
        Integer sheetNum = i / SHEET_MAX_ROW;
        workbook.createSheet("sheet" + sheetNum);
        //动态指定当前的工作表
        return workbook.getSheetAt(sheetNum);
    }

    // 获取标题总行数
    private static Integer getHeaderRowNum(List<ExcelHeaderInfo> headerInfos) {
        Integer maxRowNum = 0;
        for (ExcelHeaderInfo excelHeaderInfo : headerInfos) {
            Integer tmpRowNum = excelHeaderInfo.getLastRow();
            if (tmpRowNum > maxRowNum) maxRowNum = tmpRowNum;
        }
        return maxRowNum + 1;
    }

    // 设置总体表格样式
    private static CellStyle setCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    // 字符串转日期
    private Date parseDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (Exception e) {
            LOGGER.error("字符串转日期错误");
            e.printStackTrace();
        }
        return date;
    }

    // 发送响应结果
    public void sendHttpResponse(HttpServletResponse response, String fileName, Workbook workbook) {
        try {
            fileName += System.currentTimeMillis() + ".xlsx";
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
