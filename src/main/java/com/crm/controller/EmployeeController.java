package com.crm.controller;


import com.crm.payload.EmployeeDto;
import com.crm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto dto, BindingResult bindingResult){
        // this is the springvalidation annotation if we dont write this the data is not
        // getting validated after adding this annotation data
        // will be validated

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    EmployeeDto employeeDto = employeeService.addEmployee(dto);
        return  new ResponseEntity<>(employeeDto,HttpStatus.CREATED);


     // basically why we write here Dto from postman i am taking content to dto and from dto i am putting that to
        // entity save it and entity is again converted into controller and controller is given to Dto back.
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(
            @RequestParam Long id
    ){
       employeeService.deleteEmployee(id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity <EmployeeDto> UpdateEmployee(
            @RequestParam Long id,
            @RequestBody EmployeeDto dto
    ){
      EmployeeDto employeeDto = employeeService.updateEmployee(id, dto);
       return new ResponseEntity<>(employeeDto , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity <List<EmployeeDto>> getEmployees(
    @RequestParam(name="page size",required = false, defaultValue ="5") int pageSize ,
    @RequestParam(name="pageNo",required = false, defaultValue ="0") int pageNo

    ){
       List<EmployeeDto> employeesDto = employeeService.getEmployee(pageNo,pageSize);
       return new ResponseEntity<>(employeesDto, HttpStatus.OK);
    }
    // http://localhost:8080/api/v1/employee?id=1/1
    @GetMapping("/employeeId/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @PathVariable Long empId
    ){
        EmployeeDto dto = employeeService.getEmployeeById(empId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
