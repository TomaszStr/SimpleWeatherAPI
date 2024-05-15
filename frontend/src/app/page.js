'use client'

import Image from "next/image";
import styles from "./page.module.css";

import ReactDOM from 'react-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useState, useEffect } from 'react';


export default function Home() {
  // by default Warsaw
  const [latitude, setLatitude] = useState('52');
  const [longitude, setLongitude] = useState('21');
  // JSON - Weather
  const [data, setData] = useState(null);
  // if currently loading data
  const [loading, setLoading] = useState(false);
  // save error
  const [error, setError] = useState(null);

  useEffect(() => {
  }, []);

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await fetch(`http://localhost:8080/weather?latitude=${latitude}&longitude=${longitude}`);
      if (!response.ok) {
        throw new Error('Błąd pobierania danych: ' + response.statusText);
      }
      const jsonData = await response.json();
      setData(jsonData);
      setLoading(false);
    } catch (error) {
      console.error('ERROR:', error);
      setData(null);
      setError(error);
      setLoading(false);
    }
  };
  // for button
  const handleSubmit = async (e) => {
    e.preventDefault();
    fetchData();
  };

  function Location() {
    useEffect(() => {
      if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(function(position) {
          const lat = position.coords.latitude;
          const long= position.coords.longitude;
          setLatitude(lat);
          setLongitude(long);
        });
      } else {
        console.log('Geolocation is not supported by this browser');
      }
    }, []);
}

  const getWeatherIcon = (weatherCode) => {
    // Tutaj możesz dodać logikę mapowania kodu pogodowego WMO na ikonę
    switch (weatherCode) {
      case 'Sun':
        return <i class="fa-solid fa-sun"></i>;
      case 'Cloud':
        return <i class="fa-solid fa-cloud"></i>;
      case 'Fog':
        return <i class="fa-solid fa-smog"></i>;
      case 'Rain':
        return <i class="fa-solid fa-cloud-rain"></i>;
      case 'Snow':
        return <i class="fa-solid fa-snowflake"></i>;
      case 'Thunderstorm':
        return <i class="fa-solid fa-cloud-bolt"></i>;
      default:
        return <i class="fa-solid fa-question"></i>;
    }
  };


  const getListNames = (key)=>{
    if(key != 'time' && key != 'forecastUnits'){
      if(key == 'temperatureMin'|| key == 'temperatureMax')
        return key + '[°C]';
      else if(key == 'energyProduced')
        return key + '[kWh]';
      else
        return key;
    }
  }

  return (
    <main className={styles.main}>
      <div>
        {Location()}
      <form onSubmit={handleSubmit}>
        <label>
          Latitude:
          <input
            type="text"
            value={latitude}
            onChange={(e) => setLatitude(e.target.value)}
          />
        </label>
        <label>
          Longitude:
          <input
            type="text"
            value={longitude}
            onChange={(e) => setLongitude(e.target.value)}
          />
        </label>
        <button type="submit" disabled={loading}>
          {loading ? 'loading...' : 'Check weather'}
        </button>
      </form>
    </div>
    <div>
      <h1>WeatherForecast <i class="fa-regular fa-face-smile-wink"></i></h1>
        {data && (
          <table border="1" style={{borderCollapse: 'collapse', width: '80%'}} >
            <thead>
              <tr>
                <th>Date</th>
                {!loading && data != null && data.time.map((date) =>(
                  <th>{date}</th>
                )
                )}
              </tr>
            </thead>
            <tbody>
              {!loading && data != null && Object.keys(data).map((key,keyIndex) => (
                <tr>
                  <td>{getListNames(key)}</td>
                  {
                  data[key].map(function(item, index){
                      if(key != 'time' && key != 'forecastUnits'){
                        if(key == 'weatherCode')
                          return<td>{getWeatherIcon(item)}</td>;
                        else if(key == 'energyProduced')
                          return<td>{item.toFixed(2)}</td>;
                        return<td>{item}</td>;
                      }
                    }
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        )}
    </div>
  </main>
  );
}
