package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.PaymentPlanDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentPlanModel {
    public String getNextPaymentPlanId() throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.execute("select payplan_id from payment_plan order by payplan_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("Z%03d",newIdIndex);
        }
        return  "Z001";

    }
    public ArrayList<PaymentPlanDto> getAllPaymentPlan() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from payment_plan");
        ArrayList<PaymentPlanDto> paymentPlanDtos = new ArrayList<>();
        while (rst.next()){
            PaymentPlanDto paymentPlanDto =  new PaymentPlanDto(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6));
            paymentPlanDtos.add(paymentPlanDto);
        }
        return paymentPlanDtos;
    }
    public boolean savePaymentPlan(PaymentPlanDto paymentPlanDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into payment_plan values(?,?,?,?,?,?)", paymentPlanDto.getPayplanId(),paymentPlanDto.getAmount(),paymentPlanDto.getRate(),paymentPlanDto.getRatePrice(),paymentPlanDto.getDescription(),paymentPlanDto.getPayId());

        return  isSaved;
    }
    public boolean deletePaymentPlan(String payPlanId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from payment_plan where payplan_id=?", payPlanId);
    }
    public boolean updatePaymentPlan(PaymentPlanDto paymentPlanDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update payment_plan set  amount=?, rate=?,  rate_price=?, description=?, payment_Id=? where payplan_id=?",
                paymentPlanDto.getAmount(),
                paymentPlanDto.getRate(),
                paymentPlanDto.getRatePrice(),
                paymentPlanDto.getDescription(),
                paymentPlanDto.getPayId(),
                paymentPlanDto.getPayplanId()
        );
    }
    public ArrayList<String> getAllPayId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select pay_id from payment");

        ArrayList<String> paymentId = new ArrayList<>();

        while (rst.next()){
            paymentId.add(rst.getString(1));
        }

        return paymentId;
    }
}
