package lk.ijse.gdse.finalproject.model;

import lk.ijse.gdse.finalproject.dto.PaymentDto;
import lk.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.execute("select pay_id from payment order by pay_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("P%03d",newIdIndex);
        }
        return  "P001";

    }
    public ArrayList<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from payment");
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        while (rst.next()){
            PaymentDto paymentDto =  new PaymentDto(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4));
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }
    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into payment values(?,?,?,?)", paymentDto.getPayId(),paymentDto.getPayDate(),paymentDto.getPayMethod(),paymentDto.getAdminId());

        return  isSaved;
    }
    public boolean deletePayment(String payId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from payment where pay_id=?", payId);
    }
    public boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update payment set  pay_date=?, pay_method=?,  admin_id=? where pay_id=?",
                paymentDto.getPayDate(),
                paymentDto.getPayMethod(),
                paymentDto.getAdminId(),
                paymentDto.getPayId()
        );
    }
}
