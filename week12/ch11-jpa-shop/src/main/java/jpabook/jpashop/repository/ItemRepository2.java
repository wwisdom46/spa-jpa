package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository2 {
    @PersistenceContext
    EntityManager em;

    // save -> 저장과 수정이 동시에 되도록
    public void save(Item item) {
        if (StringUtils.isEmpty(item.getId())) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    // findOne
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // findAll
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }


}
