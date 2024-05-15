import React, { Component } from 'react';

class LocationComponent extends Component {
  componentDidMount() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(function(position) {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        console.log('Latitude:', latitude);
        console.log('Longitude:', longitude);
        // Tutaj możesz wykorzystać uzyskane współrzędne
      });
    } else {
      console.log('Geolocation is not supported by this browser');
    }
  }

  render() {
    return (
      <div>
        {this.componentDidMount()}
      </div>
    );
  }
}

export default LocationComponent;