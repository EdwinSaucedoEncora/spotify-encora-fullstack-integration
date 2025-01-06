import { RootState } from "@/lib/store";
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { url } from "inspector";

const baseQueryWithHeaders = async (args: any, api: any, extraOptions: any) => {
  if (args === null || !args.url) {
    return { error: { message: "No valid URL provided" } };
  }
  const baseQuery = fetchBaseQuery({
    baseUrl: "http://localhost:3000",
    prepareHeaders: (headers, { getState }) => {
      const state = getState() as RootState;
      const session = JSON.parse(localStorage.getItem("session") ?? "");
      const { access_token, refresh_token, expires_at } = session;
      const expiresAt = new Date(expires_at);
      const isExpired = expiresAt <= new Date();
      headers.delete("Access-Control-Allow-Credentials");
      // headers.set("credentials", "include");
      // headers.set("Accept", "*/*");
      // headers.set("Connection", "keep/alive");
      // headers.set("Accept-Encoding", "gzip, deflate, br");
      if (access_token) {
        headers.set("Authorization", `Bearer ${access_token}`);
        headers.set("X-Refresh", refresh_token);
      }

      return headers;
    },
  });
  const response = await baseQuery(args, api, extraOptions);

  if (response.meta?.response?.headers) {
    const token = response.meta.response.headers.get("Authorization");
    if (token) {
      const session = JSON.parse(localStorage.getItem("session") ?? "{}");
      session.access_token = token;
      localStorage.setItem("session", JSON.stringify(session));
    }
  }

  return response;
};

export const spotifyApi = createApi({
  reducerPath: "spotifyApi",
  baseQuery: baseQueryWithHeaders,
  endpoints: (builder) => ({
    getArtist: builder.query({
      query: (id) => {
        return {
          url: `artists/${id}`,
          method: "GET",
        };
      },
    }),
    getTopUserArtists: builder.query({
      query: () => {
        return {
          url: "me/top/artists",
          method: "GET",
        };
      },
    }),
    search: builder.query({
      query: (query) => {
        if (query) {
          return {
            url: "search",
            method: "GET",
            params: { query },
          };
        }
        return null;
      },
    }),
    getAlbumById: builder.query({
      query: (id) => ({
        url: `albums/${id}`,
        method: "GET",
      }),
    }),
  }),
});

export const {
  useSearchQuery,
  useLazySearchQuery,
  useGetAlbumByIdQuery,
  useLazyGetAlbumByIdQuery,
  useLazyGetTopUserArtistsQuery,
  useGetTopUserArtistsQuery,
  useGetArtistQuery,
} = spotifyApi;
