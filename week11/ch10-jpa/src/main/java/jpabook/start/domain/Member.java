package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;  // JPA 어노테이션 패키지

@Getter
@Setter
@Entity
@Table(name="MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    @Column(name = "USERNAME")
    private String username;

    // 연관관계 매핑
    @ManyToOne  // 다대일(N:1)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
