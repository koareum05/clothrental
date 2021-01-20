package clothrental;

import javax.persistence.*;

import com.esotericsoftware.kryo.util.IntArray;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String productId;
    private Integer qty;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Order order = new Order();
        order.setStatus(order.getStatus());
        System.out.println("##### Status : " + order.getStatus());

        if (Objects.equals(status, "Order")) {

            Ordered ordered = new Ordered();
            BeanUtils.copyProperties(this, ordered);
            ordered.publishAfterCommit();
        }
        if (Objects.equals(status, "Return")){

            Returned returned = new Returned();
            BeanUtils.copyProperties(this, returned);
            returned.publishAfterCommit();
        }
        if (Objects.equals(status, "Exchange")){
            Exchanged exchanged = new Exchanged();
            BeanUtils.copyProperties(this, exchanged);
            exchanged.publishAfterCommit();

        }
        if (Objects.equals(status, "Wash")){
            Washed washed = new Washed();
            BeanUtils.copyProperties(this, washed);
            washed.publishAfterCommit();

        }

    }

    // 서킷브레이크 테스트 시 주석 해제할 것
    @PostUpdate
    public void onPostUpdate(){

/*
    OrdereCancelled ordereCancelled = new OrdereCancelled();
        BeanUtils.copyProperties(this, ordereCancelled);
        ordereCancelled.publishAfterCommit();

    //Following code causes dependency to external APIs
    // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

    clothrental.external.Cancellation cancellation = new clothrental.external.Cancellation();
    // mappings goes here
    // 아래 this는 Order 어그리게이트
        cancellation.setOrderId(this.getId());
        cancellation.setStatus("Delivery Cancelled");
        OrderApplication.applicationContext.getBean(clothrental.external.CancellationService.class)
            .cancelship(cancellation);
*/

        WashCancelled washCancelled = new WashCancelled();
            BeanUtils.copyProperties(this, washCancelled);
            washCancelled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        clothrental.external.WashCancellation washCancellation = new clothrental.external.WashCancellation();
        // mappings goes here
        // 아래 this는 Order 어그리게이트
            washCancellation.setOrderId(this.getId());
            washCancellation.setStatus("Laundry Cancelled");
            OrderApplication.applicationContext.getBean(clothrental.external.WashCancellationService.class)
                .cancelwash(washCancellation);
    }
//
    @PreRemove
    public void onPreRemove(){
/*
        OrdereCancelled ordereCancelled = new OrdereCancelled();
        BeanUtils.copyProperties(this, ordereCancelled);
        ordereCancelled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        clothrental.external.Cancellation cancellation = new clothrental.external.Cancellation();
        // mappings goes here
        // 아래 this는 Order 어그리게이트
        cancellation.setOrderId(this.getId());
        cancellation.setStatus("Delivery Cancelled");
        OrderApplication.applicationContext.getBean(clothrental.external.CancellationService.class)
                .cancelship(cancellation);
*/

        WashCancelled washCancelled = new WashCancelled();
        BeanUtils.copyProperties(this, washCancelled);
        washCancelled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        clothrental.external.WashCancellation washCancellation = new clothrental.external.WashCancellation();
        // mappings goes here
        // 아래 this는 Order 어그리게이트
        washCancellation.setOrderId(this.getId());
        washCancellation.setStatus("Laundry Cancelled");
        OrderApplication.applicationContext.getBean(clothrental.external.WashCancellationService.class)
                .cancelwash(washCancellation);

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
