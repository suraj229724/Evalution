/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.Task;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.service.TaskService;
import cc.accenture.performanceevaluation.service.UserService;
import cc.altius.utils.DateUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author altius
 */
@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "task/addTask.htm", method = RequestMethod.GET)
    public String addTaskGET(Model map) {
        map.addAttribute("clientList", this.taskService.getClientList());
        map.addAttribute("taskTypeList", this.taskService.getTaskTypeList());
        map.addAttribute("shiftTimeList", this.taskService.getShiftTimeList());
        Task task = new Task();
        map.addAttribute("task", task);
        return "task/addTask";
    }

    @RequestMapping(value = "task/addTask.htm", method = RequestMethod.POST)
    public String addTaskPOST(@ModelAttribute("task") Task task, HttpServletRequest request, Model map) {
        this.taskService.addTask(task);
        return "redirect:/task/taskList.htm";
    }

    @RequestMapping(value = "task/taskList.htm")
    public String listTaskPOST(HttpServletRequest request, Model modelMap) {
        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
        List<Task> taskList = this.taskService.getTaskList(startDate, stopDate, userId);
        List<User> userList = this.userService.getUserList(false, "");
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("stopDate", stopDate);
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("taskList", taskList);
        return "task/taskList";
    }

    @RequestMapping(value = "/task/showEditTask.htm", method = RequestMethod.POST)
    public String showEditTask(@RequestParam(value = "taskId", required = true) int taskId, ModelMap model) {
        Task task = this.taskService.getTaskByTaskId(taskId);
        model.addAttribute("clientList", this.taskService.getClientList());
        model.addAttribute("taskTypeList", this.taskService.getTaskTypeList());
        model.addAttribute("shiftTimeList", this.taskService.getShiftTimeList());
        model.addAttribute("task", task);
        System.out.println("task = " + task);
        return "/task/editTask";
    }

    @RequestMapping(value = "/task/editTask.htm", method = RequestMethod.POST)
    public String onEditTaskSubmit(@ModelAttribute("task") Task task, Errors errors, ModelMap model, HttpServletRequest request) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:/task/taskList.htm?msg=msg.actionCancelled";
        }
        this.taskService.updateTask(task);
        return "redirect:/task/taskList.htm?msg=msg.TaskUpdated";
    }
}
