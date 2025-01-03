import { configureStore } from "@reduxjs/toolkit";
import AuthReducer from "./authSlice";
import { spotifyApi } from "@/services/spotify";
import { setupListeners } from "@reduxjs/toolkit/query";

export const makeStore = () => {
  const store = configureStore({
    reducer: {
      auth: AuthReducer,
      [spotifyApi.reducerPath]: spotifyApi.reducer,
    },
    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware().concat(spotifyApi.middleware),
  });
  setupListeners(store.dispatch);
  return store;
};

export type AppStore = ReturnType<typeof makeStore>;
export type RootState = ReturnType<AppStore["getState"]>;
export type AppDispatch = AppStore["dispatch"];
