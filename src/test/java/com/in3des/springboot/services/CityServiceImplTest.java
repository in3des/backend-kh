package com.in3des.springboot.services;

import com.in3des.springboot.exceptions.CityNotFoundException;
import com.in3des.springboot.models.City;
import com.in3des.springboot.repository.CityRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@NoArgsConstructor
public class CityServiceImplTest {

    @Mock
    private CityRepository repo;

    @InjectMocks
    private CityServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new CityServiceImpl(repo);
    }

    @Test
    public void should_find_all_cities() {
        //given
        when(repo.findAll()).thenReturn(loadData());
        //when
        List<City> list = service.getAllCities();
        //then
        assertThat(list.size()).isEqualTo(4);
        assertThat(list).isNotNull();
    }

    @Test
    public void should_update_city() throws CityNotFoundException {
        City city1 = new City(1L, "name1", "http://name1.jpg");
        City city2 = new City(1L, "nameUpd", "http://nameUpd.jpg");
        when(repo.findById(city1.getId())).thenReturn(Optional.of(city2));
        when(service.updateCity(city1)).thenReturn(city2);
        City city3 = service.updateCity(city1);
        Assert.assertEquals(city3, city2);
        verify(repo, times(1)).save(city1);
        assertThat(service.updateCity(city1)).isNotNull();
    }

//    @Test
//    public void should_update_city() throws CityNotFoundException {
//        //given
//        City city = new City(1L, "name1", "http://name1.jpg");
//        when(repo.save(Mockito.any(City.class))).thenReturn(city);
//        //when
//        City saved = service.updateCity(city);
//        //then
//        assertThat(saved.getName()).isSameAs(city.getName());
//        verify(repo, times(1)).save(city);
//        assertThat(service.updateCity(city)).isNotNull();
//    }

    @Test
    public void should_find_city_byId() throws CityNotFoundException {
        //given
        Long cityId = 1L;
        City city = new City(cityId, "name1", "http://name1.jpg");
        when(repo.findById(cityId)).thenReturn(Optional.of(city));
        //when
        City actualCity = service.getCityById(cityId);
        //then
        verify(repo, times(1)).findById(cityId);
        verifyNoMoreInteractions(repo);
        assertThat(actualCity).isEqualTo(city);
        assertThat(actualCity).isNotNull();
    }

    List<City> loadData(){

        List<City> list = new ArrayList<>();
        list.add(new City(1L, "name1", "http://name1.jpg"));
        list.add(new City(2L, "name2", "http://name2.jpg"));
        list.add(new City(3L, "name3", "http://name3.jpg"));
        list.add(new City(4L, "name4", "http://name4.jpg"));

        return list;
    }
}