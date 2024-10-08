package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Poll {

    private int id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;

    @JsonIgnore
    private User owner;
    private List<VoteOption> voteOptions;

    public Poll(int id, String question, User owner) {
        this.id = id;
        this.question = question;
        this.publishedAt = Instant.now();
        this.validUntil = this.publishedAt.plus(1, ChronoUnit.HOURS);
        this.voteOptions = new ArrayList<>();
        this.owner = owner;
        owner.getCreatedPolls().add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public VoteOption getVoteOptionByCaption(String caption) {
        for (VoteOption voteOption : voteOptions) {
            if (voteOption.getCaption().equals(caption)) {
                return voteOption;
            }
        }
        return null;
    }

    public void deletePoll() {
        owner.getCreatedPolls().remove(this);
        for (VoteOption voteOption : voteOptions) {
            for (Vote vote : voteOption.getVotes()) {
                vote.getUser().deleteVote(vote);
            }
            voteOption.deleteAllVotes();
        }
    }

}
