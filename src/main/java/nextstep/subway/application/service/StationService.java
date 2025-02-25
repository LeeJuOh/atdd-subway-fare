package nextstep.subway.application.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.common.error.exception.NotFoundException;
import nextstep.subway.application.dto.request.StationCreateRequest;
import nextstep.subway.application.dto.response.StationResponse;
import nextstep.subway.domain.entity.Station;
import nextstep.subway.domain.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public StationResponse saveStation(StationCreateRequest stationCreateRequest) {
        Station station = stationRepository.save(new Station(stationCreateRequest.getName()));
        return createStationResponse(station);
    }

    public List<StationResponse> findAllStations() {
        return stationRepository.findAll().stream()
            .map(this::createStationResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }

    private StationResponse createStationResponse(Station station) {
        return new StationResponse(
            station.getId(),
            station.getName()
        );
    }

    public Station getStationById(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(() -> new NotFoundException("not found station"));
    }
}
