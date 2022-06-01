package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 주의 실제로는 HashMap 사용하면 안됨 동시에 여러 쓰레드가 접근함 컨커런트 해쉬맵 사용 해야 함
    private static long sequence = 0; // static 주의 멀티 쓰레드 환경에서 아토믹 롱 사용 해야 함


    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); // ArrayList 로 감싸서 반환하면 엔티티랑 베타성이 되어 값이 변경이 되어도 Store는 변경되지 않는다.
        // 리턴해서 가져간게 변경이 되면 영향을 받지 않아야 해서 이렇게 다른 객체로 만들어서 전달 하는 듯 하다.
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }


}
