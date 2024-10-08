package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;

public class VoteOption {

    private String caption;
    @JsonIgnore
    private Poll poll;
    private Collection<Vote> votes;

    public VoteOption(String caption, Poll poll) {
        this.caption = caption;
        this.poll = poll;
        poll.getVoteOptions().add(this);
        this.votes = new ArrayList<Vote>();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
    }

    public void deleteVote(Vote vote) {
        this.votes.remove(vote);
    }

    public void deleteAllVotes() {
        this.votes.clear();
    }
}
