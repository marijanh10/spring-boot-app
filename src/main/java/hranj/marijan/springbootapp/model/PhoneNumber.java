package hranj.marijan.springbootapp.model;

import hranj.marijan.springbootapp.dto.PhoneNumberDto;
import hranj.marijan.springbootapp.utils.OtpGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class PhoneNumber {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 25)
    @Basic
    private String number;

    @Column(nullable = false)
    @Basic
    private Timestamp timeOtpAdded;

    @Column(nullable = false, length = 4)
    @Basic
    private char[] otp;

    @Column
    @Basic
    private boolean approved;

    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private User user;

    public PhoneNumber(PhoneNumberDto phoneNumberDto, User user) {
        this.number = phoneNumberDto.getCountryCode() + phoneNumberDto.getPhoneNumber();
        this.user = user;
        this.timeOtpAdded = new Timestamp(System.currentTimeMillis());
        this.otp = OtpGenerator.generateOTP(4);
    }

    public PhoneNumber() {}

    @Override
    public String toString() {
        return number;
    }
}
