package com.zeeyeh.jobscraft.fulltool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curtail {
    private long id;
    private String title;
    private long jobId;
    private long levelId;
    private List<IdExp> tools;
    private List<IdExp> foods;
    private List<IdExp> places;
    private List<IdExp> destructs;
    private List<IdExp> recipes;
    private List<IdExp> interacts;
    private List<IdExp> attacks;
    private List<IdExp> buffs;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdExp {
        private String id;
        private long exp;
    }

    public static enum Type {
        TOOL, FOOD, PLACE, DESTRUCT, RECIPE, INTERACT, ATTACK, BUFF
    }
}
