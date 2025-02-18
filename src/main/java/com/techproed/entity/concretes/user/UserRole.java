package com.techproed.entity.concretes.user;


import com.techproed.entity.enums.RoleType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Sınıfına Builder Pattern Özelliği kazandırarak nesne oluşturmayı daha okunabilir
// ve esnek hale getirir.
// Karmaşık yapıcı methodar (constructor) veya Setter kullanmadan nesne oluşturmamıza yardımcı olur.
// Immutable (değişmez) nesneler oluşturmayı kolaylaştırır.

public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    // Enum ın veritabanı tablosunda süttuna olarak saklanırken isim ile mi yoksa
    //sıra numarası ile mi saklanacağını belirler (STRING veye ORDINAL)
    private RoleType roleType;

    private String roleName;


}
