package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.anno.Log;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /*
    * 查询部门列表
    * */
//    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping("/depts")
    public Result list(){
        System.out.println("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /*
    * 删除部门 需要参数为部门id
    * */
/*    //方式一 通过HttpServletRequest对象
    @DeleteMapping("/depts")
    public Result deleteById(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        System.out.println("删除id为"+ id +"的数据");
        return Result.success();
    }*/
/*    //方式二 通过Spring的注解@RequestParam,该注解注意事项：请求时必须传递，否则报错
    @DeleteMapping("/depts")
    public Result deleteBuId(@RequestParam("id") Integer deptId){
        System.out.println("删除id为"+ deptId +"的数据");
        return Result.success();
    }*/
    //方式三 参数一致时即前端传进的形参和后端定义的形参一致，可以去掉注解@RequestParam
    @DeleteMapping("/depts")
    @Log
    public Result deleteBuId(Integer id){
        System.out.println("删除id为"+ id +"的数据");
        deptService.deleteById(id);
        return Result.success();
    }

/*
*  增加部门
* */
    @PostMapping("/depts")
    @Log
    public Result add(@RequestBody Dept dept){
        System.out.println("增加部门为："+dept);
        deptService.add(dept);
        return Result.success();
    }

    /*
    *  根据ID查询部门
    * */
    @GetMapping("/depts/{id}")// 这里用大括号是 占位符，因为不能把这个写死
    public Result getInfo(@PathVariable("id") Integer deptId){
        //PathVariable括号填 要接收哪一个参数名，这里要接收id，然后赋值给deptId
        // 同时 这里也可简化，路径参数名 和方法形参名一致 就可以省略PathVariable的括号
        // 如public Result getInfo(@PathVariable Integer id)
        System.out.println("根据ID查询部门："+deptId);
        Dept dept = deptService.getById(deptId);// 查询 是得返回一个对象 所以这里需要有返回值
        return Result.success(dept);// 拿到数据需要返回给前端，所以这里需要返回对象
    }

    /*
    *  修改部门
    * */
    @PutMapping("/depts")
    @Log
    public Result update(@RequestBody Dept dept){
        System.out.println("修改部门:"+dept);
        deptService.update(dept);
        return Result.success();
    }

}
