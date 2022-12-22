package jpabook.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holyeye on 2014. 3. 11..
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @NonNull
    private String id;

    @NonNull
    private String username;

    // 연관관계 매핑
    @ManyToOne  // 다대일(N:1)
    @JoinColumn(name="TEAM_ID")
    private Team team;
}
