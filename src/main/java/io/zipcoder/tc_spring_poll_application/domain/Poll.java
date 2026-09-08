package io.zipcoder.tc_spring_poll_application.domain;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POLL_ID", nullable = false, length = 255)
    private Long id;

    @Column(name = "QUESTION", nullable = false, length = 255)
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "POLL_ID")
    @OrderBy
    private Set<Option> option;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Option> getOptions() {
        return option;
    }

    public void setOptions(Set<Option> option) {
        this.option = option;
    }
}
