package com.techproed.entity.concretes.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.entity.concretes.business.Meet;
import com.techproed.entity.concretes.business.StudentFeedback;
import com.techproed.entity.concretes.business.StudentInfo;
import com.techproed.entity.enums.Day;
import com.techproed.entity.enums.Gender;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="t_user") // farklı parametreler eklemek için " , Ctrl + Space"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    @Column(unique=true)
    private String ssn;

    private String name;
    private String surname;

    //Jackson tarafından JSON formatına çevirirken (serialize) ve JSON'dan
    // Java nesnesine çevirirken (deserialize) tarih formatını özelleştirir.
    //Tarih değerini String olarak saklar ve "yyyy-MM-dd" formatına çevirir.
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String birthplace;


    /*Bu anotasyon sayesinde password alanı JSON çıktısında görünmez, ancak
    JSON'dan Java nesnesine çevrilirken alınabilir.
Güvenlik açısından şifre gibi hassas verilerin JSON yanıtında gönderilmesini
önlemek için kullanılır.
Yalnızca giriş (deserialization) için izin verir, ancak JSON çıktısında
 (serialization) yer almaz.*/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique=true)
    private String phoneNumber;

    @Column(unique=true)
    private String email;

    private Boolean buildIn;

    private String motherName;

    private String fatherName;

    private int studentNumber;

    private boolean isActive;

    private Boolean isAdvisor;

    private Long advisorTeacherId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne
    @JsonProperty(access = Access.WRITE_ONLY)
    private UserRole userRole;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;

    @ManyToMany
    @JoinTable(
            name = "user_lessonprogram",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")

    )
    private List<LessonProgram>lessonProgramList;


    @JsonIgnore
    @ManyToMany(mappedBy = "studentList")
    private List<Meet> meetList;

    @OneToOne(mappedBy = "student") // "student" StudentFeedback entity'sindeki alanı referans alacak
    private StudentFeedback studentFeedback;

}
/*Java'da "builder" deseni, özellikle karmaşık nesne oluşturma süreçlerini daha
okunabilir ve yönetilebilir hale getirmek için kullanılan bir tasarım desenidir.
Bu deseni uygulamak için elle kod yazılabileceği gibi, Lombok gibi kütüphaneler
de @Builder gibi anotasyonlar sunarak bu işlemi otomatikleştirir.

Lombok @Builder Anotasyonu Nedir?
@Builder anotasyonu, Lombok kütüphanesi tarafından sağlanır ve bir sınıf veya metod
için otomatik olarak bir "builder" sınıfı oluşturur. Bu sayede, sınıfınızın nesnelerini
daha okunabilir, zincirleme (fluent) metod çağrılarıyla oluşturabilirsiniz. Özellikle
çok sayıda parametreye sahip veya bazı parametreleri opsiyonel olan sınıflar için bu
 desen oldukça kullanışlıdır.

Temel Özellikleri
Okunabilir Kod: Nesneleri oluştururken, hangi değerin hangi alanı temsil ettiğini metod
 isimleri sayesinde rahatça görebilirsiniz.
Zincirleme (Fluent) Kullanım: Oluşturma sürecinde metodları zincirleme şekilde çağırarak
daha temiz bir kod yazabilirsiniz.
Opsiyonel Parametreler: Farklı kombinasyonlarda parametrelerin verilmesine olanak tanır,
 böylece birden fazla constructor yazma gereksinimini ortadan kaldırır.                                Builder Deseninin Avantajları
İnşa Sürecinin Yönetilmesi: Karmaşık nesnelerin adım adım inşasını sağlar.
Immutable Nesneler: Genellikle immutable (değişmez) nesneler oluştururken tercih edilir,
 çünkü nesne oluşturulduktan sonra alanlar değiştirilemez.
Kod Tekrarını Azaltır: Farklı parametre kombinasyonları için birden fazla constructor
 yazmak yerine tek bir builder yapısı kullanılır.
Sonuç
Java'da @Builder anotasyonu, özellikle Lombok kütüphanesi ile birlikte kullanıldığında,
 nesne oluşturma işlemlerini kolaylaştırır, kodun okunabilirliğini artırır ve bakımı
 daha da basitleştirir. Builder desenini uygulamak manuel olarak da yapılabilir ancak
  Lombok sayesinde bu işlem otomatikleştirilir ve yazılan kod miktarı azalır.*/