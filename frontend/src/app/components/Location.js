function Location() {
    useEffect(() => {
      if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(function(position) {
          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;
          // Tutaj możesz wykorzystać uzyskane współrzędne
        });
      } else {
        console.log('Geolocation is not supported by this browser');
      }
    }, []);
}