package org.hxy.pl.controller.excel;

import org.hxy.pl.common.Constants;
import org.hxy.pl.controller.BaseController;
import org.hxy.pl.service.excel.ExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 15-5-4.
 */
@Controller
@RequestMapping(value = "/excel")
public class ExcelController extends BaseController {

    @Autowired
    private ExcelDataService excelDataService;

    @Autowired
    private ServletContext servletContext;

    /**
     * 导出excel存储的目录
     */
    private String contextRootPath;


    @PostConstruct
    public void afterPropertiesSet() {
        contextRootPath = servletContext.getRealPath("/");
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String showImportExcelForm() {
        return "excel/importForm";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcel(
            @RequestParam("file") MultipartFile file,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {
        if(!canImport(file, model)) {
            return showImportExcelForm();
        }
        InputStream is = file.getInputStream();
        excelDataService.importExcel2007(is);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "导入成功");
        return redirectToUrl(null);
    }

    private boolean canImport(final MultipartFile file, final Model model) {
        if (file == null || file.isEmpty()) {
            model.addAttribute(Constants.ERROR, "请选择要导入的文件");
            return false;
        }

        String filename = file.getOriginalFilename().toLowerCase();
        if (!(filename.endsWith("xlsx"))) {
            model.addAttribute(Constants.ERROR, "导入的文件格式错误，允许的格式：xlsx");
            return false;
        }

        return true;
    }

}