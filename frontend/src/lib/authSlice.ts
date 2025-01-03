"use client";

import { createSlice } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";

export interface AuthState {
  token: string | undefined;
  refreshToken: string | undefined;
  expiresAt: string | undefined;
}

export const AuthSlice = createSlice({
  name: "auth",
  initialState: { token: undefined } as AuthState,
  reducers: {
    setToken: (state, action: PayloadAction<string | undefined>) => {
      console.log({ action, state: state.token });
      state.token = action.payload ?? state.token;
      console.log({ token: state.token });
    },
    setExpiresAt: (state, action: PayloadAction<string | undefined>) => {
      state.expiresAt = action.payload ?? state.expiresAt;
    },
    setRefreshToken: (state, action: PayloadAction<string | undefined>) => {
      state.refreshToken = action.payload ?? state.refreshToken;
    },
  },
});

export const { setToken, setExpiresAt, setRefreshToken } = AuthSlice.actions;

export default AuthSlice.reducer;
