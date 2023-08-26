package com.boldyrev.absence_employee_system.controllers.controllers;

import com.boldyrev.absence_employee_system.controllers.responses.CustomResponse;
import com.boldyrev.absence_employee_system.dao.AbsenceActDTO;
import com.boldyrev.absence_employee_system.dao.transport.NewOrExists;
import com.boldyrev.absence_employee_system.services.AbsenceActsService;
import com.boldyrev.absence_employee_system.util.mappers.AbsenceActMapper;
import com.boldyrev.absence_employee_system.util.validators.AbsenceActValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/absence-acts")
public class AbsenceActsController {

    private final AbsenceActsService actsService;
    private final AbsenceActMapper actMapper;
    private final AbsenceActValidator actValidator;

    @Autowired
    public AbsenceActsController(AbsenceActsService actsService, AbsenceActMapper actMapper,
        AbsenceActValidator actValidator) {
        this.actsService = actsService;
        this.actMapper = actMapper;
        this.actValidator = actValidator;
    }


    /**
     * GET endpoint для получения списка Актов
     *
     * @return страница со списком Актов
     */
    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        List<AbsenceActDTO> acts = actsService.findAll().stream().map(actMapper::actToActDTO)
            .toList();

        return new ResponseEntity<>(new CustomResponse(acts), HttpStatus.OK);
    }

    /**
     * GET endpoint для получения Акта по Id
     *
     * @param id идентификатор Акта
     * @return Акт
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(@PathVariable("id") Long id) {
        AbsenceActDTO act = actMapper.actToActDTO(actsService.findById(id));
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(new CustomResponse(act));
    }

    /**
     * POST endpoint для сохранения Акта
     *
     * @param act    новый Акт
     * @param errors ошибки валидации
     * @return сохраненный акт
     */
    @PostMapping
    public ResponseEntity<CustomResponse> create(
        @RequestBody @Validated(NewOrExists.class) AbsenceActDTO act,
        BindingResult errors) {
        actValidator.validate(act, errors);

        return new ResponseEntity<>(
            new CustomResponse(actMapper.actToActDTO(actsService.save(actMapper.actDTOToAct(act)))),
            HttpStatus.CREATED
        );
    }

    /**
     * PUT endpoint для обновления Акта
     *
     * @param id идентификатор обновляемого Акта
     * @param act    обновленный Акт
     * @param errors ошибки валидации
     * @return обновленный акт
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateById(@PathVariable("id") Long id,
        @RequestBody @Validated(NewOrExists.class) AbsenceActDTO act, BindingResult errors) {
        actValidator.validate(act, errors);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(new CustomResponse(
                actMapper.actToActDTO(actsService.update(id, actMapper.actDTOToAct(act)))));
    }

    /**
     * DELETE endpoint для удаления Акта
     *
     * @param id идентификатор удаляемого Акта
     * @return сообщение с успешным удалением
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteById(@PathVariable Long id) {
        actsService.deleteById(id);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(new CustomResponse("Absence act was delete or not exists"));
    }
}
