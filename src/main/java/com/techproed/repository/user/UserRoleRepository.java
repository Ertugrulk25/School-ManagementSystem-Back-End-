package com.techproed.repository.user;

import com.techproed.entity.concretes.user.UserRole;
import com.techproed.entity.enums.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select r from UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByUserRoleType(RoleType roleType);
}

/*1️⃣ Genel Görevi Nedir?
JpaRepository<UserRole, Long> → UserRole entity'sine CRUD (Create, Read, Update,
 Delete) işlemlerini otomatik sağlar.
findByUserRoleType(RoleType roleType) metodu → Verilen RoleType enum'ına sahip
 UserRole kaydını veri tabanından bulur.
2️⃣ findByUserRoleType Metodu Ne Yapıyor?
Bu metod, veri tabanında rol tipi (roleType) verilen bir UserRole kaydını bulmak
 için kullanılır.

📌 Özel Sorgu (@Query) Açıklaması

@Query("select r from UserRole r WHERE r.roleType = ?1")
Optional<UserRole> findByUserRoleType(RoleType roleType);
JPQL Sorgusu:
SELECT r FROM UserRole r WHERE r.roleType = ?1
UserRole tablosundaki roleType değeri, parametre olarak gelen RoleType ile eşleşen
 kaydı döndürür.
Optional<UserRole>:
Eğer eşleşen bir kayıt varsa UserRole nesnesi döner.
Eğer kayıt yoksa Optional.empty() döner (null yerine daha güvenli).*/