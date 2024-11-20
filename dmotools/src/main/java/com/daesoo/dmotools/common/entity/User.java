package com.daesoo.dmotools.common.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends TimeStamp{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;
    
    private int timerCompleteCount;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPrice> userPriceList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSeal> userSealList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Character> characterList;
    
    public void modify(String nickname) {
    	this.nickname = nickname != null ? nickname : this.nickname;
    }
    
    
    public static User create(String email, String nickname) {
    	return User.builder()
    			.email(email)
    			.nickname(nickname)
    			.build();
    }
    
    public void increaseCompleteCount() {
    	this.timerCompleteCount++;
    }
}
