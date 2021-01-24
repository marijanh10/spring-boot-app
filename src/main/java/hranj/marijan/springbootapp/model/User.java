package hranj.marijan.springbootapp.model;

import hranj.marijan.springbootapp.dto.PhoneNumberDto;
import hranj.marijan.springbootapp.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class User {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 45)
    @Basic
    private String username;

    @Column(nullable = false, length = 100)
    @Basic
    private String password;

    @Column
    @Basic
    private boolean approved;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
    private List<Image> myImages;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
    private List<PhoneNumber> phoneNumbers;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "shared_image",
            joinColumns = { @JoinColumn(name = "fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_image") }
    )
    private List<Image> imagesSharedWithMe;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public void addMyImage(Image image) {
        myImages.add(image);
    }

    public void addImageSharedWithMe(Image image) {
        imagesSharedWithMe.add(image);
    }

    public void removeMyImage(Image image) {
        myImages.remove(image);
    }

    public void removeImageSharedWithMe(Image image) {
        imagesSharedWithMe.remove(image);
    }

    public List<PhoneNumber> getApprovedPhoneNumbers() {
        return phoneNumbers.stream()
                .filter(PhoneNumber::isApproved)
                .collect(Collectors.toList());
    }

}
