package jpabook.start;

import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class queryDSL {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

//        /**
//         * QueryDSL 시작하기
//         */
        JPAQuery query = new JPAQuery(em);  // 엔티티 매니저를 생성자에 넘겨준다.
//        QMember qMember = new QMember("m"); // 쿼리 타입(Q) 생성하고 별칭을 준다. (JPQL에서 별칭으로 사용)
////        QMember qMember = QMember.member;
//        List<Member> members = query.from(qMember)
//                .where(qMember.username.eq("회원1"))
//                .orderBy(qMember.username.desc())
//                .list(qMember);
//
//        for (Member member : members) {
//            System.out.println(member.toString());
//        }

//        /**
//         * 검색 조건 쿼리
//         */
//        QItem item = QItem.item;
//        List<Item> items = query.from(item)
//                .where(item.name.eq("좋은상품").and(item.price.gt(20000)))
//                .list(item);    // 조회할 프로젝션 지정

//        /**
//         * 페이징과 정렬
//         */
//        // 기본
//        List<Member> pagedMembers = query.from(qMember)
//                .orderBy(qMember.username.desc())
//                .offset(0).limit(20) // 페이징
//                .list(qMember);
//
//        for (Member member : pagedMembers) {
//            System.out.println(member.toString());
//        }

//        // QueryModifiers 사용
//        QueryModifiers queryModifiers = new QueryModifiers(20L, 10L);   // limit, offset
//        List<Member> members = query.from(qMember)
//                .restrict(queryModifiers)
//                .list(qMember);
//        for (Member member : members) {
//            System.out.println(member.toString());
//        }

//        // listResults() 사용
//        SearchResults<Member> result = query.from(qMember)
//                .listResults(qMember);
//        System.out.println("검색된 데이터 전체 수: " + result.getTotal());
//        System.out.println(result.getLimit());
//        System.out.println(result.getOffset());
//        // 조회된 데이터
//        List<Member> members = result.getResults();
//        for (Member member : members) {
//            System.out.println(member.toString());
//        }

//        /**
//         * 조인
//         */
//        // 기본 조인
//        QMember member = QMember.member;
//        QTeam team = QTeam.team;
//
//        List<Member> members = query.from(member)
//                .join(member.team, team)
//                .where(team.name.eq("팀1"))
//                .list(member);
//        for (Member m : members) {
//            System.out.println(member.toString());
//        }

        /**
         * 서브 쿼리
         */
        // 한 건
        QMember member = QMember.member;
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = query.from(member)
                .where(member.id.eq(
                        new JPASubQuery().from(memberSub).unique(memberSub.id.max())
                ))
                .list(member.id, member.username);
        for (Tuple tuple : result) {
            System.out.println("id: "+ tuple.get(member.id));
            System.out.println("username: "+ tuple.get(member.username));
        }
    }
}
