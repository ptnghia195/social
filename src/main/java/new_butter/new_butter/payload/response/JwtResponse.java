package new_butter.new_butter.payload.response;

import java.time.LocalDate;
import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String address;
    private LocalDate dayOfBirth;
    private List<String> roles;


    public JwtResponse(){
    }

    public JwtResponse(String jwtToken, Long id, String username, String email,String fullname,String address,LocalDate dayOfBirth, List<String> roles) {
        this.token = jwtToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.dayOfBirth = dayOfBirth;
        this.fullname = fullname;
        this.address = address;
    }
    public static JwtResponse build(String jwtToken, Long id, String username, String email, String fullname,String address,LocalDate dayOfBirth, List<String> roles){
        return new JwtResponse(jwtToken, id, username, email,fullname,address,dayOfBirth, roles);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}