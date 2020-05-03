package com.kundy.excelutils.controller;

import com.kundy.excelutils.entity.AjaxResult;
import com.kundy.excelutils.entity.po.SysUserDO;
import com.kundy.excelutils.entity.po.TtlProductInfoPo;
import com.kundy.excelutils.service.TtlProductInfoService;
import com.kundy.excelutils.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author yangyl
 * @create 2020.05.03 23.30
 */
@RestController
@RequestMapping("/excelUtils")
public class ImportController {

    @GetMapping("/import")
    public AjaxResult export(MultipartFile file) throws Exception {
        ExcelUtil<SysUserDO> util = new ExcelUtil<>(SysUserDO.class);
        List<SysUserDO> userList = util.importExcel(file.getInputStream());
        // todo
        //String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success("success", userList);
    }

}
