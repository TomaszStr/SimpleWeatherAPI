import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "WeatherForecast",
  description: "7 day weather forecast",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        <script src="https://kit.fontawesome.com/f8a1f75859.js" crossOrigin="anonymous"></script>
      </head>
      <body className={inter.className}>{children}</body>
    </html>
  );
}
