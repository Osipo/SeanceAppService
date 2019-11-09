package ru.osipov.deploy.services;

import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osipov.deploy.entities.Seance;
import ru.osipov.deploy.models.SeanceInfo;
import ru.osipov.deploy.repositories.SeanceRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.time.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class SeanceServiceImpl implements SeanceService{

    private final SeanceRepository rep;

    private static final Logger logger = getLogger(SeanceServiceImpl.class);

    @Autowired
    public SeanceServiceImpl(SeanceRepository r){
        this.rep = r;
    }

    @Override
    @NonNull
    public List<SeanceInfo> getAllSeances() {
        logger.info("Get all...");
        return rep.findAll().stream().map(this::buildModel).collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<SeanceInfo> getSeancesInCinema(@Nonnull Long cid) {
        logger.info("Get seance in cinema id = '{}'",cid);
        return rep.findByCid(cid).stream().map(this::buildModel).collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<SeanceInfo> getSeancesByFilm(Long fid) {
        logger.info("Get seance of film with id  = '{}'",fid);
        return rep.findByFid(fid).stream().map(this::buildModel).collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public SeanceInfo getSeanceByFilmAndCinema(Long fid, Long cid) {
        logger.info("Get seance of film = '{}' from cinema = '{}'",fid,cid);
        return rep.findByFidAndCid(fid,cid).map(this::buildModel).orElse(new SeanceInfo(-1l,-1l, LocalDate.now()));
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public List<SeanceInfo> getSeancesByDate(String dateStr){
        logger.info("Date string = '{}'",dateStr);
        LocalDate date = LocalDate.parse(dateStr);
        return rep.findAllByDate(date).stream().map(this::buildModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeanceInfo> getSeancesByDateBetween(String dateStr, String dateEnd) {
        logger.info("Get all seances including from '{}' to '{}'",dateStr,dateEnd);
        LocalDate date1 = LocalDate.parse(dateStr);
        LocalDate date2 = LocalDate.parse(dateEnd);
        return rep.findAllByDateBetween(date1,date2).stream().map(this::buildModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeanceInfo> getSeancesByDateBefore(String dateStr) {
        logger.info("Get all seances til = '{}'",dateStr);
        LocalDate date = LocalDate.parse(dateStr);
        return rep.findAllByDateBefore(date).stream().map(this::buildModel).collect(Collectors.toList());
    }

    private SeanceInfo buildModel(Seance s){
        return new SeanceInfo(s.getCid(),s.getFid(),s.getDate());
    }
}