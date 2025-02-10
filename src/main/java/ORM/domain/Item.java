package ORM.domain;

import ORM.Exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;  // 재고

    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategories=new ArrayList<>();

    // 재고 수량 증가
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }
    // 재고 수량 감소
    public void removeStock(int stockQuantity) {
        int rest=this.stockQuantity - stockQuantity;
        if(rest < 0) {
            throw new NotEnoughStockException("stock is Negative");
        }
        this.stockQuantity = rest;
    }

}
