package com.techproed.repository.business;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.entity.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {



  @Query("select (count (e) > 0) from EducationTerm e where e.term=?1 and extract(year from e.startDate) =?2 ")
  boolean existByTermAndYear(Term term, int year);

  //girilen yilda var olan education term'leri geri dönduren sorgu
  @Query("select e from EducationTerm e where extract(year from e.startDate) =?1 ")
  List<EducationTerm>findByYear(int year);


}
