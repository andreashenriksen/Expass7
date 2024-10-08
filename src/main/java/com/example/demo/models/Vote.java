package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class Vote {

    private Instant publishedAt;
    @JsonIgnore
    private User user;
    @JsonIgnore
    private Poll poll;
    @JsonIgnore
    private VoteOption voteOption;

    public Vote(User user, Poll poll, VoteOption voteOption) {
        this.user = user;
        this.poll = poll;
        this.voteOption = voteOption;
        this.user.getAllVotes().add(this);
        this.voteOption.getVotes().add(this);
        this.publishedAt = Instant.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
