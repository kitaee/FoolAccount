package kitae.foolaccount.controller;

public class MemberForm {

    private Long count; // 개발자가 알 수 있는 회원 수
    private String id;
    private String password;
    private String password_confirm_question;
    private String password_confirm_question_answer;
    private String name;
    private String phone;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm_question() {
        return password_confirm_question;
    }

    public void setPassword_confirm_question(String password_confirm_question) {
        this.password_confirm_question = password_confirm_question;
    }

    public String getPassword_confirm_question_answer() {
        return password_confirm_question_answer;
    }

    public void setPassword_confirm_question_answer(String password_confirm_question_answer) {
        this.password_confirm_question_answer = password_confirm_question_answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
