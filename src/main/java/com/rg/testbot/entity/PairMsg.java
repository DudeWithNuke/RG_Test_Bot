package com.rg.testbot.entity;

import javax.persistence.*;

@Entity
@Table(name="pair_msg")
public class PairMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pair_msg_id_seq")
    @SequenceGenerator(name = "pair_msg_id_seq", sequenceName = "pair_msg_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private User user;

    private String userInMsg;
    private String botOutMsg;

    public PairMsg() {}
    public PairMsg(User user, String userInMsg, String botOutMsg) {
        this.user = user;
        this.userInMsg = userInMsg;
        this.botOutMsg = botOutMsg;
    }


    public User getUser() { return user; }
    public String getUserInMsg() { return userInMsg; }
    public String getBotOutMsg() { return botOutMsg; }
}