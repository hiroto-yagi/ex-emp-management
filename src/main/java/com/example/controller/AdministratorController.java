package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

import jakarta.servlet.http.HttpSession;


/**
 * 管理者登録用Conrtroller.
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    // sessionスコープを使用するための設定
    @Autowired
    private HttpSession session;

    @GetMapping("/toInsert")
    public String toInsert(InsertAdministratorForm form, Model model) {
        model.addAttribute("name", form.getName());
        model.addAttribute("mailAddress", form.getMailAddress());
        model.addAttribute("password", form.getPassword());
        return "administrator/insert";
    }

    /**
     * 管理者情報登録.
     * @param form 管理者情報フォーム
     * @return ログイン画面
     */
    @PostMapping("/insert")
    public String insert(InsertAdministratorForm form) {
        Administrator administrator = new Administrator();
        // formオブジェクトからadministratorオブジェクトにプロパティ値をコピー
        BeanUtils.copyProperties(form, administrator);
        administratorService.insert(administrator);
        return "redirect:/";
    }
    
    /**
     * ログイン画面表示.
     * @param form ログイン用フォーム
     * @return ログイン画面
     */
    @GetMapping("/")
    public String toLogin(LoginForm form, Model model) {
        model.addAttribute("mailAddress", form.getMailAddress());
        model.addAttribute("password", form.getPassword());
        return "administrator/login";
    }

    /**
     * ログイン機能.
     * @param form ログイン用フォーム
     * @return 従業員情報一覧ページにリダイレクト
     */
    @PostMapping("/login")
    public String login(LoginForm form, Model model) {
        Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());   
        if (administrator == null) {
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
            return "administrator/login";
        }
            session.setAttribute("administratorName", administrator.getName());
        return "redirect:/employee/showList";
    }

}
