package com.ccsw.tutorialreservation.reservation;

import com.ccsw.tutorialreservation.client.model.ClientDto;
import com.ccsw.tutorialreservation.game.model.GameDto;
import com.ccsw.tutorialreservation.reservation.exception.ClientAlreadyHasReservationException;
import com.ccsw.tutorialreservation.reservation.exception.ErrorResponse;
import com.ccsw.tutorialreservation.reservation.exception.GameAlreadyReservedException;
import com.ccsw.tutorialreservation.reservation.model.Reservation;
import com.ccsw.tutorialreservation.reservation.model.ReservationDto;
import com.ccsw.tutorialreservation.reservation.model.ReservationSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.ccsw.tutorialreservation.game.GameClient;
import com.ccsw.tutorialreservation.client.ClientClient;
import com.ccsw.tutorialreservation.common.jwt.JwtService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 *
 */
@Tag(name = "Reservation", description = "API of Reservation")
@RequestMapping(value = "/reservation")
@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    GameClient gameClient;

    @Autowired
    ClientClient clientClient;

    @Autowired
    JwtService jwtService;

    /**
     * Método para recuperar un listado paginado de {@link Reservation}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link ReservationDto}
     */
    @Operation(summary = "Find page", description = "Method that return a page of Reservations")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<ReservationDto> findPage(@RequestBody ReservationSearchDto dto) {

        List<GameDto> games = gameClient.findAll();
        List<ClientDto> clients = clientClient.findAll();

        Page<Reservation> page = this.reservationService.findPage(dto);

        List<ReservationDto> reservationDtos = page.getContent().stream().map(reservation ->{
            ReservationDto reservationDto = mapper.map(reservation, ReservationDto.class);
            GameDto gameDto = games.stream().filter(game -> game.getId().equals(reservation.getIdGame())).findFirst().orElse(null);
            ClientDto clientDto = clients.stream().filter(client -> client.getId().equals(reservation.getIdClient())).findFirst().orElse(null);
            reservationDto.setGame(gameDto);
            reservationDto.setClient(clientDto);

            return reservationDto;
        }).collect(Collectors.toList());

        return new PageImpl<>(reservationDtos, page.getPageable(), page.getTotalElements());
    }

    /**
     * Método para crear o actualizar un {@link Reservation}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Reservation")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ReservationDto dto, @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {

        jwtService.validateToken(authorizationHeader);

        this.reservationService.save(id, dto);
    }

    /**
     * Método para eliminar un {@link Reservation}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Reservation")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id, @RequestHeader(name = "Authorization", required = false) String authorizationHeader) throws Exception {

        jwtService.validateToken(authorizationHeader);

        this.reservationService.delete(id);
    }

    /**
     * Recupera un listado de reservas {@link Reservation}
     *
     * @return {@link List} de {@link ReservationDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Reservations")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ReservationDto> findAll() {

        List<GameDto> games = this.gameClient.findAll();
        List<ClientDto> clients = this.clientClient.findAll();

        List<Reservation> reservations = this.reservationService.findAll();

        return reservations.stream()
                .map(reservation -> {
                    ReservationDto reservationDto = mapper.map(reservation, ReservationDto.class);
                    GameDto gameDto = games.stream().filter(game -> game.getId().equals(reservation.getIdGame())).findFirst().orElse(null);
                    ClientDto clientDto = clients.stream().filter(client -> client.getId().equals(reservation.getIdClient())).findFirst().orElse(null);

                    reservationDto.setGame(gameDto);
                    reservationDto.setClient(clientDto);

                    return reservationDto;
                }).collect(Collectors.toList());
    }

    @ExceptionHandler(value = GameAlreadyReservedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleGameAlreadyReservedException(GameAlreadyReservedException error) {
        return new ErrorResponse(error.getMessage());
    }

    @ExceptionHandler(value = ClientAlreadyHasReservationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleClientAlreadyHasReservationException(ClientAlreadyHasReservationException error) {
        return new ErrorResponse(error.getMessage());
    }
}
