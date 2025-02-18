package com.techproed.controller.business;

import com.techproed.payload.request.business.EducationTermRequest;
import com.techproed.payload.response.business.EducationTermResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.service.businnes.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {


  private final EducationTermService educationTermService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean')")
  @PostMapping("/save")
  public ResponseMessage<EducationTermResponse>save(
          @Valid @RequestBody EducationTermRequest educationTermRequest) {
    return educationTermService.save(educationTermRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @PutMapping("/update/{educationTermId}")
  public ResponseMessage<EducationTermResponse>updateEducationTerm(
          @Valid @RequestBody EducationTermRequest educationTermRequest,
          @PathVariable Long educationTermId){
    return educationTermService.updateEducationTerm(educationTermRequest,educationTermId);
  }



  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getAll")
  public List<EducationTermResponse>getAllEducationTerms(){
    return educationTermService.getAllEducationTerms();
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/{educationTermId}")
  public EducationTermResponse getEducationTerm(@PathVariable Long educationTermId) {
    return educationTermService.getEducationTermById(educationTermId);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getByPage")
  public Page<EducationTermResponse>getByPage(
          @RequestParam(value = "page",defaultValue = "0") int page,
          @RequestParam (value = "size",defaultValue = "10") int size,
          @RequestParam (value = "sort",defaultValue = "term") String sort,
          @RequestParam (value = "type",defaultValue = "desc") String type) {
    return educationTermService.getByPage(page,size,sort,type);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @DeleteMapping("/delete/{educationTermId}")
  public ResponseMessage deleteEducationTerm(@PathVariable Long educationTermId) {
    return educationTermService.deleteById(educationTermId);
  }






}