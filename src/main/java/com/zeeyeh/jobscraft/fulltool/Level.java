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
public class Level {
    private long id;
    private String name;
    private String title;
    private long maxExp;
    private long nextId;
    private List<LevelPlayer> players;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LevelPlayer {
        private String name;
        private long jobId;
        private long exp;
    }
}
