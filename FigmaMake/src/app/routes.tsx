import { createBrowserRouter } from "react-router";
import { LoginPage } from "./pages/LoginPage";
import { CalendarPage } from "./pages/CalendarPage";

export const router = createBrowserRouter([
  {
    path: "/",
    Component: LoginPage,
  },
  {
    path: "/calendario",
    Component: CalendarPage,
  },
  {
    path: "*",
    Component: LoginPage,
  },
]);
