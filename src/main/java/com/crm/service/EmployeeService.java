package com.crm.service;

import com.crm.entity.Employee;
import com.crm.exception.ResourceNotFound;
import com.crm.payload.EmployeeDto;
import com.crm.repository.EmployeeRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDto addEmployee(EmployeeDto dto){
        Employee employee = mapToEntity(dto);
    Employee emp =  employeeRepository.save(employee);
   EmployeeDto employeeDto = mapToDto(emp);
    return employeeDto ;

    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
      Employee employee = mapToEntity(dto);
      employee.setId(id);
        Employee updateEmployee =   employeeRepository.save(employee);
    EmployeeDto employeeDto =  mapToDto(updateEmployee);
    return employeeDto;
    }

    public List<EmployeeDto> getEmployee(int pageNo, int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize);//here i just created object that contain detail about pageno and page size
        Page<Employee> all = employeeRepository.findAll(page);
        //now convert all Page employees to List employee
        List<Employee> employees = all.getContent();


        List<EmployeeDto> dto = employees.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
    return dto;

    }

   EmployeeDto mapToDto(Employee employee){

        //by doing this line of code whenever i call this method supply an entity object to it
       // that entity object will be converted into dto object and that dto is what it is going to return
       //this method convert entity to dto object
       EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);

//       EmployeeDto dto = new EmployeeDto();
//       dto.setId(employee.getId());
//       dto.setName(employee.getName());
//       dto.setEmailId(employee.getEmailId());
//       dto.setMobile(employee.getMobile());
       return dto;
    }

    Employee mapToEntity(EmployeeDto dto){
       Employee emp = modelMapper.map(dto, Employee.class);
        return emp;
    }

    public EmployeeDto getEmployeeById(long empId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(
                () ->new ResourceNotFound("Employee not found with id " + empId));

      EmployeeDto dto =   mapToDto(employee);
      return dto;


    }
}
