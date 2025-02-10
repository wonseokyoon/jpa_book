package ORM.Service;

import ORM.domain.*;
import ORM.repository.ItemRepository;
import ORM.repository.MemberRepository;
import ORM.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    // 조회
    public List<Order> findAll() {
        List<Order> list=orderRepository.findAll();
        if(list.isEmpty()){
            throw new RuntimeException("list is empty");
        }
        return list;
    }

    // 주문
    @Transactional
    public Long createOrder(Long membeId,Long itemId,int count) {
        // 엔티티 조회
        Member member = memberService.findById(membeId);
        Item item = itemService.findById(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        // 주문 생성
        Order order=Order.createOrder(member,delivery,orderItem);

        // 주문 저장
        orderRepository.save(order);

        // id 반환
        return order.getId();
    }

    // 취소
    @Transactional
    public void cancleOrder(Long orderId) {
        Order order=findById(orderId);
        order.cancleOrder();
    }


    // 검색
    public Order findById(Long orderId) {
        Optional<Order> order=orderRepository.findById(orderId);
        if(order.isPresent()){
            return order.get();
        }else throw new RuntimeException("order is not found");
    }

}
