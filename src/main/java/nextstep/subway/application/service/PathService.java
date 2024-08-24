package nextstep.subway.application.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.common.error.exception.NotFoundException;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberDetailCustom;
import nextstep.member.domain.MemberRepository;
import nextstep.subway.application.dto.response.PathResponse;
import nextstep.subway.application.dto.response.PathResponse.StationDto;
import nextstep.subway.application.exception.CanNotFindPathException;
import nextstep.subway.domain.entity.Line;
import nextstep.subway.domain.entity.Path;
import nextstep.subway.domain.entity.Station;
import nextstep.subway.domain.entity.SubWayFare;
import nextstep.subway.domain.entity.SubwayMap;
import nextstep.subway.domain.enums.PathSearchType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PathService {

    private final StationService stationService;
    private final LineService lineService;

    private final MemberRepository memberRepository;

    public PathResponse findPath(Long sourceStationId, Long targetStationId, PathSearchType type,
        MemberDetailCustom memberDetailCustom) {

        Station source = stationService.getStationById(sourceStationId);
        Station target = stationService.getStationById(targetStationId);
        List<Line> lines = lineService.getLines();
        SubwayMap subwayMap = new SubwayMap(lines);
        Path shortestPath = subwayMap.findShortestPath(source, target, type)
            .orElseThrow(() -> new CanNotFindPathException("Unable to find the shortest path."));
        SubWayFare subWayFare = getSubWayFare(memberDetailCustom, shortestPath);
        return new PathResponse(
            shortestPath.getPathStations(target).stream()
                .map(StationDto::new)
                .collect(Collectors.toList()),
            shortestPath.getDistance(),
            shortestPath.getDuration(),
            subWayFare.getTotalFare()
        );
    }

    private SubWayFare getSubWayFare(MemberDetailCustom memberDetailCustom, Path shortestPath) {
        if (memberDetailCustom.isEmpty()) {
            return new SubWayFare(shortestPath);
        }
        Member member = memberRepository.findByEmail(memberDetailCustom.getId())
            .orElseThrow(NotFoundException::new);
        return new SubWayFare(shortestPath, member.getAge());
    }


}
