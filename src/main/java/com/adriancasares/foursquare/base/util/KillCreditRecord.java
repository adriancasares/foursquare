package com.adriancasares.foursquare.base.util;

import com.adriancasares.foursquare.base.Person;

public class KillCreditRecord {

    private Person attacker;
    private long timestamp;

    public Person getAttacker() {
        return attacker;
    }

    public void setAttacker(Person attacker) {
        this.attacker = attacker;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
