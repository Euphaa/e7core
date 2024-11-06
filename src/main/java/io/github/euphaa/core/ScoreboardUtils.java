package io.github.euphaa.core;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScoreboardUtils
{
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORZ]");
    private static final Pattern SIDEBAR_EMOJI_PATTERN = Pattern.compile("[\uD83D\uDD2B\uD83C\uDF6B\uD83D\uDCA3\uD83D\uDC7D\uD83D\uDD2E\uD83D\uDC0D\uD83D\uDC7E\uD83C\uDF20\uD83C\uDF6D\u26BD\uD83C\uDFC0\uD83D\uDC79\uD83C\uDF81\uD83C\uDF89\uD83C\uDF82]+");
    private static List<String> scoreboardLines;
    private static List<String> strippedScoreboardLines;
    private static String scoreboardTitle;
    private static long lastScoreboard = 0;


    public static String getScoreboardTitle()
    {
        refreshScoreboard();
        return scoreboardTitle;
    }

    public static List<String> getScoreboard()
    {
        refreshScoreboard();
        return scoreboardLines;
    }

    public static List<String> getUnformattedScoreboard()
    {
        refreshScoreboard();
        return strippedScoreboardLines;
    }

    /**
     * refreshes scoreboard and saves a plain copy and a
     * formatted copy to scoreboardLines and strippedScoreboardLines
     */
    public static void refreshScoreboard()
    {
        if (System.currentTimeMillis() - lastScoreboard < 40) return;

        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);

        scoreboardTitle = sidebarObjective.getDisplayName();

        Collection<Score> scores = scoreboard.getSortedScores(sidebarObjective);
        List<Score> filteredScores = scores.stream().filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toList());
        if (filteredScores.size() > 15)
        {
            scores = Lists.newArrayList(Iterables.skip(filteredScores, scores.size() - 15));
        }
        else
        {
            scores = filteredScores;
        }

        Collections.reverse(filteredScores);

        scoreboardLines = new ArrayList<>();
        strippedScoreboardLines = new ArrayList<>();

        for (Score line : scores)
        {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(line.getPlayerName());
            String scoreboardLine = ScorePlayerTeam.formatPlayerName(team, line.getPlayerName()).trim();
            String cleansedScoreboardLine = SIDEBAR_EMOJI_PATTERN.matcher(scoreboardLine).replaceAll("");
            String strippedCleansedScoreboardLine = stripColor(cleansedScoreboardLine);

            scoreboardLines.add(cleansedScoreboardLine);
            strippedScoreboardLines.add(strippedCleansedScoreboardLine);
        }

        lastScoreboard = System.currentTimeMillis();
    }

    /**
     * strips minecraft color and formatting codes from text
     * @param input
     * @return
     */
    private static String stripColor(String input)
    {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

}
