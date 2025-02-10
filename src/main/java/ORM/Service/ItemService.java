package ORM.Service;


import ORM.Exception.NotEnoughStockException;
import ORM.domain.Item;
import ORM.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 전체 조회
    public List<Item> findAll() {
        List<Item>itemList=itemRepository.findAll();
        if(itemList.isEmpty()){
            throw new IllegalStateException("itemList is empty");
        }
        return itemList;
    }

    // 상품 저장
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    // 상품 검색(Id)
    public Item findById(Long id) {
        Optional<Item> item=itemRepository.findById(id);
        if(item.isPresent()){
            return item.get();
        }else throw new IllegalStateException("item not found");
    }


}
