package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        // 1. JPA 사용 준비 /  엔티티 매니저 팩토리 생성
        // Cost가 크므로, 애플리케이션 실행할 때 한 번만 생성하고 공유하여 사용한다.
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("jpabook");

        // 2. 엔티티 매니저를 생성하여 CRUD 진행
        // DB 커넥션과 관계가 있으므로, Thread 간에 공유하거나 재사용하면 안된다.
        EntityManager entityManager = emFactory.createEntityManager();

        // 3. 트랜잭션 생성 및 시작
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            // 트랜잭션 시작 / 항상 트랜잭션 안에서 데이터를 변경해야 한다.
            transaction.begin();

            // CRUD와 관련된 비즈니스 로직
            logic(entityManager);

            // 트랜잭션 커밋
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            // 트랜잭션 롤백
            transaction.rollback();
        } finally {
            // 엔티티 매니저 종료
            entityManager.close();
        }

        // 엔티티 매니저 팩토리 종료
        emFactory.close();
    }

    public static void logic(EntityManager entityManager) {
        // Member 객체 생성
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("spa-jpa");
        member.setAge(2);

        // 저장
        entityManager.persist(member);

        // 수정 / 엔티티의 값만 변경하면 UPDATE문이 생성되어 DB 값을 변경한다.
        member.setAge(20);

        // 1건 조회
        Member findMember = entityManager.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회 (검색 쿼리를 위한 쿼리 작성 기능 / JPQL 사용)
        // 조회 대상은 테이블이 아닌 엔티티 객체가 대상이다.
        List<Member> members = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        // 삭제
        entityManager.remove(member);

    }
}
