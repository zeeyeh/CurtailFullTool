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
public class Job {
    private long id;
    private List<String> players;
    private String name;
    private String title;
}
