package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by 1001218 on 15. 4. 5..
 */
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            //비즈니스 로직
            testSave(em);
            testRead(em);
            queryLogicJoin(em);
            updateRelation(em);
            deleteRelation(em);
            biDirection(em);
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    // 회원과 팀 저장
    public static void testSave(EntityManager em) {
        // 팀 정보 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원 정보 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    // 객체 그래프 탐색
    public static void testRead(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam();
        System.out.println("[객체 그래프 탐색] 팀 이름 === " + team.getName());
    }

    // 객체지향 쿼리 JPQL 사용
    public static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member m join m.team t where t.name = :teamName";  // 파라미터 바인딩
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();
        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    // 연관관계 수정
    public static void updateRelation(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 새로운 팀 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);

    }

    // 연관관계 제거
    public static void deleteRelation(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        member.setTeam(null);
    }

    // 일대다 컬렉션 조회
    public static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();   // 팀 -> 회원 찾기 (객체 그래프 탐색)

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
    }
}
