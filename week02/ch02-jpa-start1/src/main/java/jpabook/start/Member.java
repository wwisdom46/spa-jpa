package jpabook.start;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;  // JPA 어노테이션 패키지

/**
 * User: HolyEyE
 * Date: 13. 5. 24. Time: 오후 7:43
 */
@Getter
@Setter
@Entity
@Table(name="MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age;
}
